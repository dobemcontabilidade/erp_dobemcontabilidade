import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IAnexoRequerido } from 'app/entities/anexo-requerido/anexo-requerido.model';
import { AnexoRequeridoService } from 'app/entities/anexo-requerido/service/anexo-requerido.service';
import { IEnquadramento } from 'app/entities/enquadramento/enquadramento.model';
import { EnquadramentoService } from 'app/entities/enquadramento/service/enquadramento.service';
import { ITributacao } from 'app/entities/tributacao/tributacao.model';
import { TributacaoService } from 'app/entities/tributacao/service/tributacao.service';
import { IRamo } from 'app/entities/ramo/ramo.model';
import { RamoService } from 'app/entities/ramo/service/ramo.service';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';
import { IEmpresaModelo } from 'app/entities/empresa-modelo/empresa-modelo.model';
import { EmpresaModeloService } from 'app/entities/empresa-modelo/service/empresa-modelo.service';
import { IAnexoRequeridoEmpresa } from '../anexo-requerido-empresa.model';
import { AnexoRequeridoEmpresaService } from '../service/anexo-requerido-empresa.service';
import { AnexoRequeridoEmpresaFormService } from './anexo-requerido-empresa-form.service';

import { AnexoRequeridoEmpresaUpdateComponent } from './anexo-requerido-empresa-update.component';

describe('AnexoRequeridoEmpresa Management Update Component', () => {
  let comp: AnexoRequeridoEmpresaUpdateComponent;
  let fixture: ComponentFixture<AnexoRequeridoEmpresaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let anexoRequeridoEmpresaFormService: AnexoRequeridoEmpresaFormService;
  let anexoRequeridoEmpresaService: AnexoRequeridoEmpresaService;
  let anexoRequeridoService: AnexoRequeridoService;
  let enquadramentoService: EnquadramentoService;
  let tributacaoService: TributacaoService;
  let ramoService: RamoService;
  let empresaService: EmpresaService;
  let empresaModeloService: EmpresaModeloService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AnexoRequeridoEmpresaUpdateComponent],
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
      .overrideTemplate(AnexoRequeridoEmpresaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AnexoRequeridoEmpresaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    anexoRequeridoEmpresaFormService = TestBed.inject(AnexoRequeridoEmpresaFormService);
    anexoRequeridoEmpresaService = TestBed.inject(AnexoRequeridoEmpresaService);
    anexoRequeridoService = TestBed.inject(AnexoRequeridoService);
    enquadramentoService = TestBed.inject(EnquadramentoService);
    tributacaoService = TestBed.inject(TributacaoService);
    ramoService = TestBed.inject(RamoService);
    empresaService = TestBed.inject(EmpresaService);
    empresaModeloService = TestBed.inject(EmpresaModeloService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call AnexoRequerido query and add missing value', () => {
      const anexoRequeridoEmpresa: IAnexoRequeridoEmpresa = { id: 456 };
      const anexoRequerido: IAnexoRequerido = { id: 28947 };
      anexoRequeridoEmpresa.anexoRequerido = anexoRequerido;

      const anexoRequeridoCollection: IAnexoRequerido[] = [{ id: 8897 }];
      jest.spyOn(anexoRequeridoService, 'query').mockReturnValue(of(new HttpResponse({ body: anexoRequeridoCollection })));
      const additionalAnexoRequeridos = [anexoRequerido];
      const expectedCollection: IAnexoRequerido[] = [...additionalAnexoRequeridos, ...anexoRequeridoCollection];
      jest.spyOn(anexoRequeridoService, 'addAnexoRequeridoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ anexoRequeridoEmpresa });
      comp.ngOnInit();

      expect(anexoRequeridoService.query).toHaveBeenCalled();
      expect(anexoRequeridoService.addAnexoRequeridoToCollectionIfMissing).toHaveBeenCalledWith(
        anexoRequeridoCollection,
        ...additionalAnexoRequeridos.map(expect.objectContaining),
      );
      expect(comp.anexoRequeridosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Enquadramento query and add missing value', () => {
      const anexoRequeridoEmpresa: IAnexoRequeridoEmpresa = { id: 456 };
      const enquadramento: IEnquadramento = { id: 9572 };
      anexoRequeridoEmpresa.enquadramento = enquadramento;

      const enquadramentoCollection: IEnquadramento[] = [{ id: 31208 }];
      jest.spyOn(enquadramentoService, 'query').mockReturnValue(of(new HttpResponse({ body: enquadramentoCollection })));
      const additionalEnquadramentos = [enquadramento];
      const expectedCollection: IEnquadramento[] = [...additionalEnquadramentos, ...enquadramentoCollection];
      jest.spyOn(enquadramentoService, 'addEnquadramentoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ anexoRequeridoEmpresa });
      comp.ngOnInit();

      expect(enquadramentoService.query).toHaveBeenCalled();
      expect(enquadramentoService.addEnquadramentoToCollectionIfMissing).toHaveBeenCalledWith(
        enquadramentoCollection,
        ...additionalEnquadramentos.map(expect.objectContaining),
      );
      expect(comp.enquadramentosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Tributacao query and add missing value', () => {
      const anexoRequeridoEmpresa: IAnexoRequeridoEmpresa = { id: 456 };
      const tributacao: ITributacao = { id: 24515 };
      anexoRequeridoEmpresa.tributacao = tributacao;

      const tributacaoCollection: ITributacao[] = [{ id: 29946 }];
      jest.spyOn(tributacaoService, 'query').mockReturnValue(of(new HttpResponse({ body: tributacaoCollection })));
      const additionalTributacaos = [tributacao];
      const expectedCollection: ITributacao[] = [...additionalTributacaos, ...tributacaoCollection];
      jest.spyOn(tributacaoService, 'addTributacaoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ anexoRequeridoEmpresa });
      comp.ngOnInit();

      expect(tributacaoService.query).toHaveBeenCalled();
      expect(tributacaoService.addTributacaoToCollectionIfMissing).toHaveBeenCalledWith(
        tributacaoCollection,
        ...additionalTributacaos.map(expect.objectContaining),
      );
      expect(comp.tributacaosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Ramo query and add missing value', () => {
      const anexoRequeridoEmpresa: IAnexoRequeridoEmpresa = { id: 456 };
      const ramo: IRamo = { id: 4951 };
      anexoRequeridoEmpresa.ramo = ramo;

      const ramoCollection: IRamo[] = [{ id: 22866 }];
      jest.spyOn(ramoService, 'query').mockReturnValue(of(new HttpResponse({ body: ramoCollection })));
      const additionalRamos = [ramo];
      const expectedCollection: IRamo[] = [...additionalRamos, ...ramoCollection];
      jest.spyOn(ramoService, 'addRamoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ anexoRequeridoEmpresa });
      comp.ngOnInit();

      expect(ramoService.query).toHaveBeenCalled();
      expect(ramoService.addRamoToCollectionIfMissing).toHaveBeenCalledWith(
        ramoCollection,
        ...additionalRamos.map(expect.objectContaining),
      );
      expect(comp.ramosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Empresa query and add missing value', () => {
      const anexoRequeridoEmpresa: IAnexoRequeridoEmpresa = { id: 456 };
      const empresa: IEmpresa = { id: 19294 };
      anexoRequeridoEmpresa.empresa = empresa;

      const empresaCollection: IEmpresa[] = [{ id: 25146 }];
      jest.spyOn(empresaService, 'query').mockReturnValue(of(new HttpResponse({ body: empresaCollection })));
      const additionalEmpresas = [empresa];
      const expectedCollection: IEmpresa[] = [...additionalEmpresas, ...empresaCollection];
      jest.spyOn(empresaService, 'addEmpresaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ anexoRequeridoEmpresa });
      comp.ngOnInit();

      expect(empresaService.query).toHaveBeenCalled();
      expect(empresaService.addEmpresaToCollectionIfMissing).toHaveBeenCalledWith(
        empresaCollection,
        ...additionalEmpresas.map(expect.objectContaining),
      );
      expect(comp.empresasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call EmpresaModelo query and add missing value', () => {
      const anexoRequeridoEmpresa: IAnexoRequeridoEmpresa = { id: 456 };
      const empresaModelo: IEmpresaModelo = { id: 28993 };
      anexoRequeridoEmpresa.empresaModelo = empresaModelo;

      const empresaModeloCollection: IEmpresaModelo[] = [{ id: 16553 }];
      jest.spyOn(empresaModeloService, 'query').mockReturnValue(of(new HttpResponse({ body: empresaModeloCollection })));
      const additionalEmpresaModelos = [empresaModelo];
      const expectedCollection: IEmpresaModelo[] = [...additionalEmpresaModelos, ...empresaModeloCollection];
      jest.spyOn(empresaModeloService, 'addEmpresaModeloToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ anexoRequeridoEmpresa });
      comp.ngOnInit();

      expect(empresaModeloService.query).toHaveBeenCalled();
      expect(empresaModeloService.addEmpresaModeloToCollectionIfMissing).toHaveBeenCalledWith(
        empresaModeloCollection,
        ...additionalEmpresaModelos.map(expect.objectContaining),
      );
      expect(comp.empresaModelosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const anexoRequeridoEmpresa: IAnexoRequeridoEmpresa = { id: 456 };
      const anexoRequerido: IAnexoRequerido = { id: 15922 };
      anexoRequeridoEmpresa.anexoRequerido = anexoRequerido;
      const enquadramento: IEnquadramento = { id: 1279 };
      anexoRequeridoEmpresa.enquadramento = enquadramento;
      const tributacao: ITributacao = { id: 20380 };
      anexoRequeridoEmpresa.tributacao = tributacao;
      const ramo: IRamo = { id: 24871 };
      anexoRequeridoEmpresa.ramo = ramo;
      const empresa: IEmpresa = { id: 30270 };
      anexoRequeridoEmpresa.empresa = empresa;
      const empresaModelo: IEmpresaModelo = { id: 22747 };
      anexoRequeridoEmpresa.empresaModelo = empresaModelo;

      activatedRoute.data = of({ anexoRequeridoEmpresa });
      comp.ngOnInit();

      expect(comp.anexoRequeridosSharedCollection).toContain(anexoRequerido);
      expect(comp.enquadramentosSharedCollection).toContain(enquadramento);
      expect(comp.tributacaosSharedCollection).toContain(tributacao);
      expect(comp.ramosSharedCollection).toContain(ramo);
      expect(comp.empresasSharedCollection).toContain(empresa);
      expect(comp.empresaModelosSharedCollection).toContain(empresaModelo);
      expect(comp.anexoRequeridoEmpresa).toEqual(anexoRequeridoEmpresa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnexoRequeridoEmpresa>>();
      const anexoRequeridoEmpresa = { id: 123 };
      jest.spyOn(anexoRequeridoEmpresaFormService, 'getAnexoRequeridoEmpresa').mockReturnValue(anexoRequeridoEmpresa);
      jest.spyOn(anexoRequeridoEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anexoRequeridoEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: anexoRequeridoEmpresa }));
      saveSubject.complete();

      // THEN
      expect(anexoRequeridoEmpresaFormService.getAnexoRequeridoEmpresa).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(anexoRequeridoEmpresaService.update).toHaveBeenCalledWith(expect.objectContaining(anexoRequeridoEmpresa));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnexoRequeridoEmpresa>>();
      const anexoRequeridoEmpresa = { id: 123 };
      jest.spyOn(anexoRequeridoEmpresaFormService, 'getAnexoRequeridoEmpresa').mockReturnValue({ id: null });
      jest.spyOn(anexoRequeridoEmpresaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anexoRequeridoEmpresa: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: anexoRequeridoEmpresa }));
      saveSubject.complete();

      // THEN
      expect(anexoRequeridoEmpresaFormService.getAnexoRequeridoEmpresa).toHaveBeenCalled();
      expect(anexoRequeridoEmpresaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnexoRequeridoEmpresa>>();
      const anexoRequeridoEmpresa = { id: 123 };
      jest.spyOn(anexoRequeridoEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anexoRequeridoEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(anexoRequeridoEmpresaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareAnexoRequerido', () => {
      it('Should forward to anexoRequeridoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(anexoRequeridoService, 'compareAnexoRequerido');
        comp.compareAnexoRequerido(entity, entity2);
        expect(anexoRequeridoService.compareAnexoRequerido).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareEnquadramento', () => {
      it('Should forward to enquadramentoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(enquadramentoService, 'compareEnquadramento');
        comp.compareEnquadramento(entity, entity2);
        expect(enquadramentoService.compareEnquadramento).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareTributacao', () => {
      it('Should forward to tributacaoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(tributacaoService, 'compareTributacao');
        comp.compareTributacao(entity, entity2);
        expect(tributacaoService.compareTributacao).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareRamo', () => {
      it('Should forward to ramoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(ramoService, 'compareRamo');
        comp.compareRamo(entity, entity2);
        expect(ramoService.compareRamo).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareEmpresa', () => {
      it('Should forward to empresaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(empresaService, 'compareEmpresa');
        comp.compareEmpresa(entity, entity2);
        expect(empresaService.compareEmpresa).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareEmpresaModelo', () => {
      it('Should forward to empresaModeloService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(empresaModeloService, 'compareEmpresaModelo');
        comp.compareEmpresaModelo(entity, entity2);
        expect(empresaModeloService.compareEmpresaModelo).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
