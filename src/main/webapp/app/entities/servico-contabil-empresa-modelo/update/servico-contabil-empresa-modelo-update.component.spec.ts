import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IEmpresaModelo } from 'app/entities/empresa-modelo/empresa-modelo.model';
import { EmpresaModeloService } from 'app/entities/empresa-modelo/service/empresa-modelo.service';
import { IServicoContabil } from 'app/entities/servico-contabil/servico-contabil.model';
import { ServicoContabilService } from 'app/entities/servico-contabil/service/servico-contabil.service';
import { IServicoContabilEmpresaModelo } from '../servico-contabil-empresa-modelo.model';
import { ServicoContabilEmpresaModeloService } from '../service/servico-contabil-empresa-modelo.service';
import { ServicoContabilEmpresaModeloFormService } from './servico-contabil-empresa-modelo-form.service';

import { ServicoContabilEmpresaModeloUpdateComponent } from './servico-contabil-empresa-modelo-update.component';

describe('ServicoContabilEmpresaModelo Management Update Component', () => {
  let comp: ServicoContabilEmpresaModeloUpdateComponent;
  let fixture: ComponentFixture<ServicoContabilEmpresaModeloUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let servicoContabilEmpresaModeloFormService: ServicoContabilEmpresaModeloFormService;
  let servicoContabilEmpresaModeloService: ServicoContabilEmpresaModeloService;
  let empresaModeloService: EmpresaModeloService;
  let servicoContabilService: ServicoContabilService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ServicoContabilEmpresaModeloUpdateComponent],
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
      .overrideTemplate(ServicoContabilEmpresaModeloUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ServicoContabilEmpresaModeloUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    servicoContabilEmpresaModeloFormService = TestBed.inject(ServicoContabilEmpresaModeloFormService);
    servicoContabilEmpresaModeloService = TestBed.inject(ServicoContabilEmpresaModeloService);
    empresaModeloService = TestBed.inject(EmpresaModeloService);
    servicoContabilService = TestBed.inject(ServicoContabilService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call EmpresaModelo query and add missing value', () => {
      const servicoContabilEmpresaModelo: IServicoContabilEmpresaModelo = { id: 456 };
      const empresaModelo: IEmpresaModelo = { id: 25121 };
      servicoContabilEmpresaModelo.empresaModelo = empresaModelo;

      const empresaModeloCollection: IEmpresaModelo[] = [{ id: 14986 }];
      jest.spyOn(empresaModeloService, 'query').mockReturnValue(of(new HttpResponse({ body: empresaModeloCollection })));
      const additionalEmpresaModelos = [empresaModelo];
      const expectedCollection: IEmpresaModelo[] = [...additionalEmpresaModelos, ...empresaModeloCollection];
      jest.spyOn(empresaModeloService, 'addEmpresaModeloToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ servicoContabilEmpresaModelo });
      comp.ngOnInit();

      expect(empresaModeloService.query).toHaveBeenCalled();
      expect(empresaModeloService.addEmpresaModeloToCollectionIfMissing).toHaveBeenCalledWith(
        empresaModeloCollection,
        ...additionalEmpresaModelos.map(expect.objectContaining),
      );
      expect(comp.empresaModelosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ServicoContabil query and add missing value', () => {
      const servicoContabilEmpresaModelo: IServicoContabilEmpresaModelo = { id: 456 };
      const servicoContabil: IServicoContabil = { id: 10691 };
      servicoContabilEmpresaModelo.servicoContabil = servicoContabil;

      const servicoContabilCollection: IServicoContabil[] = [{ id: 32524 }];
      jest.spyOn(servicoContabilService, 'query').mockReturnValue(of(new HttpResponse({ body: servicoContabilCollection })));
      const additionalServicoContabils = [servicoContabil];
      const expectedCollection: IServicoContabil[] = [...additionalServicoContabils, ...servicoContabilCollection];
      jest.spyOn(servicoContabilService, 'addServicoContabilToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ servicoContabilEmpresaModelo });
      comp.ngOnInit();

      expect(servicoContabilService.query).toHaveBeenCalled();
      expect(servicoContabilService.addServicoContabilToCollectionIfMissing).toHaveBeenCalledWith(
        servicoContabilCollection,
        ...additionalServicoContabils.map(expect.objectContaining),
      );
      expect(comp.servicoContabilsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const servicoContabilEmpresaModelo: IServicoContabilEmpresaModelo = { id: 456 };
      const empresaModelo: IEmpresaModelo = { id: 495 };
      servicoContabilEmpresaModelo.empresaModelo = empresaModelo;
      const servicoContabil: IServicoContabil = { id: 32429 };
      servicoContabilEmpresaModelo.servicoContabil = servicoContabil;

      activatedRoute.data = of({ servicoContabilEmpresaModelo });
      comp.ngOnInit();

      expect(comp.empresaModelosSharedCollection).toContain(empresaModelo);
      expect(comp.servicoContabilsSharedCollection).toContain(servicoContabil);
      expect(comp.servicoContabilEmpresaModelo).toEqual(servicoContabilEmpresaModelo);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IServicoContabilEmpresaModelo>>();
      const servicoContabilEmpresaModelo = { id: 123 };
      jest.spyOn(servicoContabilEmpresaModeloFormService, 'getServicoContabilEmpresaModelo').mockReturnValue(servicoContabilEmpresaModelo);
      jest.spyOn(servicoContabilEmpresaModeloService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ servicoContabilEmpresaModelo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: servicoContabilEmpresaModelo }));
      saveSubject.complete();

      // THEN
      expect(servicoContabilEmpresaModeloFormService.getServicoContabilEmpresaModelo).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(servicoContabilEmpresaModeloService.update).toHaveBeenCalledWith(expect.objectContaining(servicoContabilEmpresaModelo));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IServicoContabilEmpresaModelo>>();
      const servicoContabilEmpresaModelo = { id: 123 };
      jest.spyOn(servicoContabilEmpresaModeloFormService, 'getServicoContabilEmpresaModelo').mockReturnValue({ id: null });
      jest.spyOn(servicoContabilEmpresaModeloService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ servicoContabilEmpresaModelo: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: servicoContabilEmpresaModelo }));
      saveSubject.complete();

      // THEN
      expect(servicoContabilEmpresaModeloFormService.getServicoContabilEmpresaModelo).toHaveBeenCalled();
      expect(servicoContabilEmpresaModeloService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IServicoContabilEmpresaModelo>>();
      const servicoContabilEmpresaModelo = { id: 123 };
      jest.spyOn(servicoContabilEmpresaModeloService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ servicoContabilEmpresaModelo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(servicoContabilEmpresaModeloService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEmpresaModelo', () => {
      it('Should forward to empresaModeloService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(empresaModeloService, 'compareEmpresaModelo');
        comp.compareEmpresaModelo(entity, entity2);
        expect(empresaModeloService.compareEmpresaModelo).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareServicoContabil', () => {
      it('Should forward to servicoContabilService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(servicoContabilService, 'compareServicoContabil');
        comp.compareServicoContabil(entity, entity2);
        expect(servicoContabilService.compareServicoContabil).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
