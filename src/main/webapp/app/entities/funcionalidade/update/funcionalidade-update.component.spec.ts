import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IModulo } from 'app/entities/modulo/modulo.model';
import { ModuloService } from 'app/entities/modulo/service/modulo.service';
import { FuncionalidadeService } from '../service/funcionalidade.service';
import { IFuncionalidade } from '../funcionalidade.model';
import { FuncionalidadeFormService } from './funcionalidade-form.service';

import { FuncionalidadeUpdateComponent } from './funcionalidade-update.component';

describe('Funcionalidade Management Update Component', () => {
  let comp: FuncionalidadeUpdateComponent;
  let fixture: ComponentFixture<FuncionalidadeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let funcionalidadeFormService: FuncionalidadeFormService;
  let funcionalidadeService: FuncionalidadeService;
  let moduloService: ModuloService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [FuncionalidadeUpdateComponent],
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
      .overrideTemplate(FuncionalidadeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FuncionalidadeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    funcionalidadeFormService = TestBed.inject(FuncionalidadeFormService);
    funcionalidadeService = TestBed.inject(FuncionalidadeService);
    moduloService = TestBed.inject(ModuloService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Modulo query and add missing value', () => {
      const funcionalidade: IFuncionalidade = { id: 456 };
      const modulo: IModulo = { id: 12053 };
      funcionalidade.modulo = modulo;

      const moduloCollection: IModulo[] = [{ id: 9001 }];
      jest.spyOn(moduloService, 'query').mockReturnValue(of(new HttpResponse({ body: moduloCollection })));
      const additionalModulos = [modulo];
      const expectedCollection: IModulo[] = [...additionalModulos, ...moduloCollection];
      jest.spyOn(moduloService, 'addModuloToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ funcionalidade });
      comp.ngOnInit();

      expect(moduloService.query).toHaveBeenCalled();
      expect(moduloService.addModuloToCollectionIfMissing).toHaveBeenCalledWith(
        moduloCollection,
        ...additionalModulos.map(expect.objectContaining),
      );
      expect(comp.modulosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const funcionalidade: IFuncionalidade = { id: 456 };
      const modulo: IModulo = { id: 1942 };
      funcionalidade.modulo = modulo;

      activatedRoute.data = of({ funcionalidade });
      comp.ngOnInit();

      expect(comp.modulosSharedCollection).toContain(modulo);
      expect(comp.funcionalidade).toEqual(funcionalidade);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFuncionalidade>>();
      const funcionalidade = { id: 123 };
      jest.spyOn(funcionalidadeFormService, 'getFuncionalidade').mockReturnValue(funcionalidade);
      jest.spyOn(funcionalidadeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ funcionalidade });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: funcionalidade }));
      saveSubject.complete();

      // THEN
      expect(funcionalidadeFormService.getFuncionalidade).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(funcionalidadeService.update).toHaveBeenCalledWith(expect.objectContaining(funcionalidade));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFuncionalidade>>();
      const funcionalidade = { id: 123 };
      jest.spyOn(funcionalidadeFormService, 'getFuncionalidade').mockReturnValue({ id: null });
      jest.spyOn(funcionalidadeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ funcionalidade: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: funcionalidade }));
      saveSubject.complete();

      // THEN
      expect(funcionalidadeFormService.getFuncionalidade).toHaveBeenCalled();
      expect(funcionalidadeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFuncionalidade>>();
      const funcionalidade = { id: 123 };
      jest.spyOn(funcionalidadeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ funcionalidade });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(funcionalidadeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareModulo', () => {
      it('Should forward to moduloService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(moduloService, 'compareModulo');
        comp.compareModulo(entity, entity2);
        expect(moduloService.compareModulo).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
