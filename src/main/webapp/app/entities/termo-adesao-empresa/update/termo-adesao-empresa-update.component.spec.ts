import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';
import { IPlanoContabil } from 'app/entities/plano-contabil/plano-contabil.model';
import { PlanoContabilService } from 'app/entities/plano-contabil/service/plano-contabil.service';
import { ITermoAdesaoEmpresa } from '../termo-adesao-empresa.model';
import { TermoAdesaoEmpresaService } from '../service/termo-adesao-empresa.service';
import { TermoAdesaoEmpresaFormService } from './termo-adesao-empresa-form.service';

import { TermoAdesaoEmpresaUpdateComponent } from './termo-adesao-empresa-update.component';

describe('TermoAdesaoEmpresa Management Update Component', () => {
  let comp: TermoAdesaoEmpresaUpdateComponent;
  let fixture: ComponentFixture<TermoAdesaoEmpresaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let termoAdesaoEmpresaFormService: TermoAdesaoEmpresaFormService;
  let termoAdesaoEmpresaService: TermoAdesaoEmpresaService;
  let empresaService: EmpresaService;
  let planoContabilService: PlanoContabilService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TermoAdesaoEmpresaUpdateComponent],
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
      .overrideTemplate(TermoAdesaoEmpresaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TermoAdesaoEmpresaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    termoAdesaoEmpresaFormService = TestBed.inject(TermoAdesaoEmpresaFormService);
    termoAdesaoEmpresaService = TestBed.inject(TermoAdesaoEmpresaService);
    empresaService = TestBed.inject(EmpresaService);
    planoContabilService = TestBed.inject(PlanoContabilService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Empresa query and add missing value', () => {
      const termoAdesaoEmpresa: ITermoAdesaoEmpresa = { id: 456 };
      const empresa: IEmpresa = { id: 918 };
      termoAdesaoEmpresa.empresa = empresa;

      const empresaCollection: IEmpresa[] = [{ id: 11405 }];
      jest.spyOn(empresaService, 'query').mockReturnValue(of(new HttpResponse({ body: empresaCollection })));
      const additionalEmpresas = [empresa];
      const expectedCollection: IEmpresa[] = [...additionalEmpresas, ...empresaCollection];
      jest.spyOn(empresaService, 'addEmpresaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ termoAdesaoEmpresa });
      comp.ngOnInit();

      expect(empresaService.query).toHaveBeenCalled();
      expect(empresaService.addEmpresaToCollectionIfMissing).toHaveBeenCalledWith(
        empresaCollection,
        ...additionalEmpresas.map(expect.objectContaining),
      );
      expect(comp.empresasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call PlanoContabil query and add missing value', () => {
      const termoAdesaoEmpresa: ITermoAdesaoEmpresa = { id: 456 };
      const planoContabil: IPlanoContabil = { id: 14650 };
      termoAdesaoEmpresa.planoContabil = planoContabil;

      const planoContabilCollection: IPlanoContabil[] = [{ id: 12313 }];
      jest.spyOn(planoContabilService, 'query').mockReturnValue(of(new HttpResponse({ body: planoContabilCollection })));
      const additionalPlanoContabils = [planoContabil];
      const expectedCollection: IPlanoContabil[] = [...additionalPlanoContabils, ...planoContabilCollection];
      jest.spyOn(planoContabilService, 'addPlanoContabilToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ termoAdesaoEmpresa });
      comp.ngOnInit();

      expect(planoContabilService.query).toHaveBeenCalled();
      expect(planoContabilService.addPlanoContabilToCollectionIfMissing).toHaveBeenCalledWith(
        planoContabilCollection,
        ...additionalPlanoContabils.map(expect.objectContaining),
      );
      expect(comp.planoContabilsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const termoAdesaoEmpresa: ITermoAdesaoEmpresa = { id: 456 };
      const empresa: IEmpresa = { id: 20238 };
      termoAdesaoEmpresa.empresa = empresa;
      const planoContabil: IPlanoContabil = { id: 15073 };
      termoAdesaoEmpresa.planoContabil = planoContabil;

      activatedRoute.data = of({ termoAdesaoEmpresa });
      comp.ngOnInit();

      expect(comp.empresasSharedCollection).toContain(empresa);
      expect(comp.planoContabilsSharedCollection).toContain(planoContabil);
      expect(comp.termoAdesaoEmpresa).toEqual(termoAdesaoEmpresa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITermoAdesaoEmpresa>>();
      const termoAdesaoEmpresa = { id: 123 };
      jest.spyOn(termoAdesaoEmpresaFormService, 'getTermoAdesaoEmpresa').mockReturnValue(termoAdesaoEmpresa);
      jest.spyOn(termoAdesaoEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ termoAdesaoEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: termoAdesaoEmpresa }));
      saveSubject.complete();

      // THEN
      expect(termoAdesaoEmpresaFormService.getTermoAdesaoEmpresa).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(termoAdesaoEmpresaService.update).toHaveBeenCalledWith(expect.objectContaining(termoAdesaoEmpresa));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITermoAdesaoEmpresa>>();
      const termoAdesaoEmpresa = { id: 123 };
      jest.spyOn(termoAdesaoEmpresaFormService, 'getTermoAdesaoEmpresa').mockReturnValue({ id: null });
      jest.spyOn(termoAdesaoEmpresaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ termoAdesaoEmpresa: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: termoAdesaoEmpresa }));
      saveSubject.complete();

      // THEN
      expect(termoAdesaoEmpresaFormService.getTermoAdesaoEmpresa).toHaveBeenCalled();
      expect(termoAdesaoEmpresaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITermoAdesaoEmpresa>>();
      const termoAdesaoEmpresa = { id: 123 };
      jest.spyOn(termoAdesaoEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ termoAdesaoEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(termoAdesaoEmpresaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEmpresa', () => {
      it('Should forward to empresaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(empresaService, 'compareEmpresa');
        comp.compareEmpresa(entity, entity2);
        expect(empresaService.compareEmpresa).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePlanoContabil', () => {
      it('Should forward to planoContabilService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(planoContabilService, 'comparePlanoContabil');
        comp.comparePlanoContabil(entity, entity2);
        expect(planoContabilService.comparePlanoContabil).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
