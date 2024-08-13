import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { ISegmentoCnae } from 'app/entities/segmento-cnae/segmento-cnae.model';
import { SegmentoCnaeService } from 'app/entities/segmento-cnae/service/segmento-cnae.service';
import { IRamo } from 'app/entities/ramo/ramo.model';
import { RamoService } from 'app/entities/ramo/service/ramo.service';
import { IEnquadramento } from 'app/entities/enquadramento/enquadramento.model';
import { EnquadramentoService } from 'app/entities/enquadramento/service/enquadramento.service';
import { ITributacao } from 'app/entities/tributacao/tributacao.model';
import { TributacaoService } from 'app/entities/tributacao/service/tributacao.service';
import { ICidade } from 'app/entities/cidade/cidade.model';
import { CidadeService } from 'app/entities/cidade/service/cidade.service';
import { IEmpresaModelo } from '../empresa-modelo.model';
import { EmpresaModeloService } from '../service/empresa-modelo.service';
import { EmpresaModeloFormService } from './empresa-modelo-form.service';

import { EmpresaModeloUpdateComponent } from './empresa-modelo-update.component';

describe('EmpresaModelo Management Update Component', () => {
  let comp: EmpresaModeloUpdateComponent;
  let fixture: ComponentFixture<EmpresaModeloUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let empresaModeloFormService: EmpresaModeloFormService;
  let empresaModeloService: EmpresaModeloService;
  let segmentoCnaeService: SegmentoCnaeService;
  let ramoService: RamoService;
  let enquadramentoService: EnquadramentoService;
  let tributacaoService: TributacaoService;
  let cidadeService: CidadeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [EmpresaModeloUpdateComponent],
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
      .overrideTemplate(EmpresaModeloUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EmpresaModeloUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    empresaModeloFormService = TestBed.inject(EmpresaModeloFormService);
    empresaModeloService = TestBed.inject(EmpresaModeloService);
    segmentoCnaeService = TestBed.inject(SegmentoCnaeService);
    ramoService = TestBed.inject(RamoService);
    enquadramentoService = TestBed.inject(EnquadramentoService);
    tributacaoService = TestBed.inject(TributacaoService);
    cidadeService = TestBed.inject(CidadeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call SegmentoCnae query and add missing value', () => {
      const empresaModelo: IEmpresaModelo = { id: 456 };
      const segmentoCnaes: ISegmentoCnae[] = [{ id: 5906 }];
      empresaModelo.segmentoCnaes = segmentoCnaes;

      const segmentoCnaeCollection: ISegmentoCnae[] = [{ id: 21186 }];
      jest.spyOn(segmentoCnaeService, 'query').mockReturnValue(of(new HttpResponse({ body: segmentoCnaeCollection })));
      const additionalSegmentoCnaes = [...segmentoCnaes];
      const expectedCollection: ISegmentoCnae[] = [...additionalSegmentoCnaes, ...segmentoCnaeCollection];
      jest.spyOn(segmentoCnaeService, 'addSegmentoCnaeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ empresaModelo });
      comp.ngOnInit();

      expect(segmentoCnaeService.query).toHaveBeenCalled();
      expect(segmentoCnaeService.addSegmentoCnaeToCollectionIfMissing).toHaveBeenCalledWith(
        segmentoCnaeCollection,
        ...additionalSegmentoCnaes.map(expect.objectContaining),
      );
      expect(comp.segmentoCnaesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Ramo query and add missing value', () => {
      const empresaModelo: IEmpresaModelo = { id: 456 };
      const ramo: IRamo = { id: 2352 };
      empresaModelo.ramo = ramo;

      const ramoCollection: IRamo[] = [{ id: 11325 }];
      jest.spyOn(ramoService, 'query').mockReturnValue(of(new HttpResponse({ body: ramoCollection })));
      const additionalRamos = [ramo];
      const expectedCollection: IRamo[] = [...additionalRamos, ...ramoCollection];
      jest.spyOn(ramoService, 'addRamoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ empresaModelo });
      comp.ngOnInit();

      expect(ramoService.query).toHaveBeenCalled();
      expect(ramoService.addRamoToCollectionIfMissing).toHaveBeenCalledWith(
        ramoCollection,
        ...additionalRamos.map(expect.objectContaining),
      );
      expect(comp.ramosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Enquadramento query and add missing value', () => {
      const empresaModelo: IEmpresaModelo = { id: 456 };
      const enquadramento: IEnquadramento = { id: 7760 };
      empresaModelo.enquadramento = enquadramento;

      const enquadramentoCollection: IEnquadramento[] = [{ id: 167 }];
      jest.spyOn(enquadramentoService, 'query').mockReturnValue(of(new HttpResponse({ body: enquadramentoCollection })));
      const additionalEnquadramentos = [enquadramento];
      const expectedCollection: IEnquadramento[] = [...additionalEnquadramentos, ...enquadramentoCollection];
      jest.spyOn(enquadramentoService, 'addEnquadramentoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ empresaModelo });
      comp.ngOnInit();

      expect(enquadramentoService.query).toHaveBeenCalled();
      expect(enquadramentoService.addEnquadramentoToCollectionIfMissing).toHaveBeenCalledWith(
        enquadramentoCollection,
        ...additionalEnquadramentos.map(expect.objectContaining),
      );
      expect(comp.enquadramentosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Tributacao query and add missing value', () => {
      const empresaModelo: IEmpresaModelo = { id: 456 };
      const tributacao: ITributacao = { id: 30899 };
      empresaModelo.tributacao = tributacao;

      const tributacaoCollection: ITributacao[] = [{ id: 22986 }];
      jest.spyOn(tributacaoService, 'query').mockReturnValue(of(new HttpResponse({ body: tributacaoCollection })));
      const additionalTributacaos = [tributacao];
      const expectedCollection: ITributacao[] = [...additionalTributacaos, ...tributacaoCollection];
      jest.spyOn(tributacaoService, 'addTributacaoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ empresaModelo });
      comp.ngOnInit();

      expect(tributacaoService.query).toHaveBeenCalled();
      expect(tributacaoService.addTributacaoToCollectionIfMissing).toHaveBeenCalledWith(
        tributacaoCollection,
        ...additionalTributacaos.map(expect.objectContaining),
      );
      expect(comp.tributacaosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Cidade query and add missing value', () => {
      const empresaModelo: IEmpresaModelo = { id: 456 };
      const cidade: ICidade = { id: 9855 };
      empresaModelo.cidade = cidade;

      const cidadeCollection: ICidade[] = [{ id: 17786 }];
      jest.spyOn(cidadeService, 'query').mockReturnValue(of(new HttpResponse({ body: cidadeCollection })));
      const additionalCidades = [cidade];
      const expectedCollection: ICidade[] = [...additionalCidades, ...cidadeCollection];
      jest.spyOn(cidadeService, 'addCidadeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ empresaModelo });
      comp.ngOnInit();

      expect(cidadeService.query).toHaveBeenCalled();
      expect(cidadeService.addCidadeToCollectionIfMissing).toHaveBeenCalledWith(
        cidadeCollection,
        ...additionalCidades.map(expect.objectContaining),
      );
      expect(comp.cidadesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const empresaModelo: IEmpresaModelo = { id: 456 };
      const segmentoCnae: ISegmentoCnae = { id: 29846 };
      empresaModelo.segmentoCnaes = [segmentoCnae];
      const ramo: IRamo = { id: 22667 };
      empresaModelo.ramo = ramo;
      const enquadramento: IEnquadramento = { id: 25641 };
      empresaModelo.enquadramento = enquadramento;
      const tributacao: ITributacao = { id: 21559 };
      empresaModelo.tributacao = tributacao;
      const cidade: ICidade = { id: 20498 };
      empresaModelo.cidade = cidade;

      activatedRoute.data = of({ empresaModelo });
      comp.ngOnInit();

      expect(comp.segmentoCnaesSharedCollection).toContain(segmentoCnae);
      expect(comp.ramosSharedCollection).toContain(ramo);
      expect(comp.enquadramentosSharedCollection).toContain(enquadramento);
      expect(comp.tributacaosSharedCollection).toContain(tributacao);
      expect(comp.cidadesSharedCollection).toContain(cidade);
      expect(comp.empresaModelo).toEqual(empresaModelo);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmpresaModelo>>();
      const empresaModelo = { id: 123 };
      jest.spyOn(empresaModeloFormService, 'getEmpresaModelo').mockReturnValue(empresaModelo);
      jest.spyOn(empresaModeloService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ empresaModelo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: empresaModelo }));
      saveSubject.complete();

      // THEN
      expect(empresaModeloFormService.getEmpresaModelo).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(empresaModeloService.update).toHaveBeenCalledWith(expect.objectContaining(empresaModelo));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmpresaModelo>>();
      const empresaModelo = { id: 123 };
      jest.spyOn(empresaModeloFormService, 'getEmpresaModelo').mockReturnValue({ id: null });
      jest.spyOn(empresaModeloService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ empresaModelo: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: empresaModelo }));
      saveSubject.complete();

      // THEN
      expect(empresaModeloFormService.getEmpresaModelo).toHaveBeenCalled();
      expect(empresaModeloService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmpresaModelo>>();
      const empresaModelo = { id: 123 };
      jest.spyOn(empresaModeloService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ empresaModelo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(empresaModeloService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareSegmentoCnae', () => {
      it('Should forward to segmentoCnaeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(segmentoCnaeService, 'compareSegmentoCnae');
        comp.compareSegmentoCnae(entity, entity2);
        expect(segmentoCnaeService.compareSegmentoCnae).toHaveBeenCalledWith(entity, entity2);
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

    describe('compareCidade', () => {
      it('Should forward to cidadeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(cidadeService, 'compareCidade');
        comp.compareCidade(entity, entity2);
        expect(cidadeService.compareCidade).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
