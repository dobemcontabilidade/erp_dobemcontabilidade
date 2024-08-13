import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { ISistema } from 'app/entities/sistema/sistema.model';
import { SistemaService } from 'app/entities/sistema/service/sistema.service';
import { ModuloService } from '../service/modulo.service';
import { IModulo } from '../modulo.model';
import { ModuloFormService } from './modulo-form.service';

import { ModuloUpdateComponent } from './modulo-update.component';

describe('Modulo Management Update Component', () => {
  let comp: ModuloUpdateComponent;
  let fixture: ComponentFixture<ModuloUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let moduloFormService: ModuloFormService;
  let moduloService: ModuloService;
  let sistemaService: SistemaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ModuloUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ModuloUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ModuloUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    moduloFormService = TestBed.inject(ModuloFormService);
    moduloService = TestBed.inject(ModuloService);
    sistemaService = TestBed.inject(SistemaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Sistema query and add missing value', () => {
      const modulo: IModulo = { id: 456 };
      const sistema: ISistema = { id: 15469 };
      modulo.sistema = sistema;

      const sistemaCollection: ISistema[] = [{ id: 9377 }];
      jest.spyOn(sistemaService, 'query').mockReturnValue(of(new HttpResponse({ body: sistemaCollection })));
      const additionalSistemas = [sistema];
      const expectedCollection: ISistema[] = [...additionalSistemas, ...sistemaCollection];
      jest.spyOn(sistemaService, 'addSistemaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ modulo });
      comp.ngOnInit();

      expect(sistemaService.query).toHaveBeenCalled();
      expect(sistemaService.addSistemaToCollectionIfMissing).toHaveBeenCalledWith(
        sistemaCollection,
        ...additionalSistemas.map(expect.objectContaining),
      );
      expect(comp.sistemasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const modulo: IModulo = { id: 456 };
      const sistema: ISistema = { id: 22261 };
      modulo.sistema = sistema;

      activatedRoute.data = of({ modulo });
      comp.ngOnInit();

      expect(comp.sistemasSharedCollection).toContain(sistema);
      expect(comp.modulo).toEqual(modulo);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IModulo>>();
      const modulo = { id: 123 };
      jest.spyOn(moduloFormService, 'getModulo').mockReturnValue(modulo);
      jest.spyOn(moduloService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ modulo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: modulo }));
      saveSubject.complete();

      // THEN
      expect(moduloFormService.getModulo).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(moduloService.update).toHaveBeenCalledWith(expect.objectContaining(modulo));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IModulo>>();
      const modulo = { id: 123 };
      jest.spyOn(moduloFormService, 'getModulo').mockReturnValue({ id: null });
      jest.spyOn(moduloService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ modulo: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: modulo }));
      saveSubject.complete();

      // THEN
      expect(moduloFormService.getModulo).toHaveBeenCalled();
      expect(moduloService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IModulo>>();
      const modulo = { id: 123 };
      jest.spyOn(moduloService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ modulo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(moduloService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareSistema', () => {
      it('Should forward to sistemaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(sistemaService, 'compareSistema');
        comp.compareSistema(entity, entity2);
        expect(sistemaService.compareSistema).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
