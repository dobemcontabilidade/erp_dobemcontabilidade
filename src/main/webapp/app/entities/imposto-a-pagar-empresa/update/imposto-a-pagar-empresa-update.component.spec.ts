import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IImpostoEmpresa } from 'app/entities/imposto-empresa/imposto-empresa.model';
import { ImpostoEmpresaService } from 'app/entities/imposto-empresa/service/imposto-empresa.service';
import { ImpostoAPagarEmpresaService } from '../service/imposto-a-pagar-empresa.service';
import { IImpostoAPagarEmpresa } from '../imposto-a-pagar-empresa.model';
import { ImpostoAPagarEmpresaFormService } from './imposto-a-pagar-empresa-form.service';

import { ImpostoAPagarEmpresaUpdateComponent } from './imposto-a-pagar-empresa-update.component';

describe('ImpostoAPagarEmpresa Management Update Component', () => {
  let comp: ImpostoAPagarEmpresaUpdateComponent;
  let fixture: ComponentFixture<ImpostoAPagarEmpresaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let impostoAPagarEmpresaFormService: ImpostoAPagarEmpresaFormService;
  let impostoAPagarEmpresaService: ImpostoAPagarEmpresaService;
  let impostoEmpresaService: ImpostoEmpresaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ImpostoAPagarEmpresaUpdateComponent],
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
      .overrideTemplate(ImpostoAPagarEmpresaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ImpostoAPagarEmpresaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    impostoAPagarEmpresaFormService = TestBed.inject(ImpostoAPagarEmpresaFormService);
    impostoAPagarEmpresaService = TestBed.inject(ImpostoAPagarEmpresaService);
    impostoEmpresaService = TestBed.inject(ImpostoEmpresaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ImpostoEmpresa query and add missing value', () => {
      const impostoAPagarEmpresa: IImpostoAPagarEmpresa = { id: 456 };
      const imposto: IImpostoEmpresa = { id: 17007 };
      impostoAPagarEmpresa.imposto = imposto;

      const impostoEmpresaCollection: IImpostoEmpresa[] = [{ id: 7573 }];
      jest.spyOn(impostoEmpresaService, 'query').mockReturnValue(of(new HttpResponse({ body: impostoEmpresaCollection })));
      const additionalImpostoEmpresas = [imposto];
      const expectedCollection: IImpostoEmpresa[] = [...additionalImpostoEmpresas, ...impostoEmpresaCollection];
      jest.spyOn(impostoEmpresaService, 'addImpostoEmpresaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ impostoAPagarEmpresa });
      comp.ngOnInit();

      expect(impostoEmpresaService.query).toHaveBeenCalled();
      expect(impostoEmpresaService.addImpostoEmpresaToCollectionIfMissing).toHaveBeenCalledWith(
        impostoEmpresaCollection,
        ...additionalImpostoEmpresas.map(expect.objectContaining),
      );
      expect(comp.impostoEmpresasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const impostoAPagarEmpresa: IImpostoAPagarEmpresa = { id: 456 };
      const imposto: IImpostoEmpresa = { id: 26029 };
      impostoAPagarEmpresa.imposto = imposto;

      activatedRoute.data = of({ impostoAPagarEmpresa });
      comp.ngOnInit();

      expect(comp.impostoEmpresasSharedCollection).toContain(imposto);
      expect(comp.impostoAPagarEmpresa).toEqual(impostoAPagarEmpresa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IImpostoAPagarEmpresa>>();
      const impostoAPagarEmpresa = { id: 123 };
      jest.spyOn(impostoAPagarEmpresaFormService, 'getImpostoAPagarEmpresa').mockReturnValue(impostoAPagarEmpresa);
      jest.spyOn(impostoAPagarEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ impostoAPagarEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: impostoAPagarEmpresa }));
      saveSubject.complete();

      // THEN
      expect(impostoAPagarEmpresaFormService.getImpostoAPagarEmpresa).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(impostoAPagarEmpresaService.update).toHaveBeenCalledWith(expect.objectContaining(impostoAPagarEmpresa));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IImpostoAPagarEmpresa>>();
      const impostoAPagarEmpresa = { id: 123 };
      jest.spyOn(impostoAPagarEmpresaFormService, 'getImpostoAPagarEmpresa').mockReturnValue({ id: null });
      jest.spyOn(impostoAPagarEmpresaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ impostoAPagarEmpresa: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: impostoAPagarEmpresa }));
      saveSubject.complete();

      // THEN
      expect(impostoAPagarEmpresaFormService.getImpostoAPagarEmpresa).toHaveBeenCalled();
      expect(impostoAPagarEmpresaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IImpostoAPagarEmpresa>>();
      const impostoAPagarEmpresa = { id: 123 };
      jest.spyOn(impostoAPagarEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ impostoAPagarEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(impostoAPagarEmpresaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareImpostoEmpresa', () => {
      it('Should forward to impostoEmpresaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(impostoEmpresaService, 'compareImpostoEmpresa');
        comp.compareImpostoEmpresa(entity, entity2);
        expect(impostoEmpresaService.compareImpostoEmpresa).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
