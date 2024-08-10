import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { ISegmentoCnae } from 'app/entities/segmento-cnae/segmento-cnae.model';
import { SegmentoCnaeService } from 'app/entities/segmento-cnae/service/segmento-cnae.service';
import { IRamo } from 'app/entities/ramo/ramo.model';
import { RamoService } from 'app/entities/ramo/service/ramo.service';
import { ITributacao } from 'app/entities/tributacao/tributacao.model';
import { TributacaoService } from 'app/entities/tributacao/service/tributacao.service';
import { IEnquadramento } from 'app/entities/enquadramento/enquadramento.model';
import { EnquadramentoService } from 'app/entities/enquadramento/service/enquadramento.service';
import { IEmpresa } from '../empresa.model';
import { EmpresaService } from '../service/empresa.service';
import { EmpresaFormService } from './empresa-form.service';

import { EmpresaUpdateComponent } from './empresa-update.component';

describe('Empresa Management Update Component', () => {
  let comp: EmpresaUpdateComponent;
  let fixture: ComponentFixture<EmpresaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let empresaFormService: EmpresaFormService;
  let empresaService: EmpresaService;
  let segmentoCnaeService: SegmentoCnaeService;
  let ramoService: RamoService;
  let tributacaoService: TributacaoService;
  let enquadramentoService: EnquadramentoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [EmpresaUpdateComponent],
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
      .overrideTemplate(EmpresaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EmpresaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    empresaFormService = TestBed.inject(EmpresaFormService);
    empresaService = TestBed.inject(EmpresaService);
    segmentoCnaeService = TestBed.inject(SegmentoCnaeService);
    ramoService = TestBed.inject(RamoService);
    tributacaoService = TestBed.inject(TributacaoService);
    enquadramentoService = TestBed.inject(EnquadramentoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call SegmentoCnae query and add missing value', () => {
      const empresa: IEmpresa = { id: 456 };
      const segmentoCnaes: ISegmentoCnae[] = [{ id: 17003 }];
      empresa.segmentoCnaes = segmentoCnaes;

      const segmentoCnaeCollection: ISegmentoCnae[] = [{ id: 22982 }];
      jest.spyOn(segmentoCnaeService, 'query').mockReturnValue(of(new HttpResponse({ body: segmentoCnaeCollection })));
      const additionalSegmentoCnaes = [...segmentoCnaes];
      const expectedCollection: ISegmentoCnae[] = [...additionalSegmentoCnaes, ...segmentoCnaeCollection];
      jest.spyOn(segmentoCnaeService, 'addSegmentoCnaeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ empresa });
      comp.ngOnInit();

      expect(segmentoCnaeService.query).toHaveBeenCalled();
      expect(segmentoCnaeService.addSegmentoCnaeToCollectionIfMissing).toHaveBeenCalledWith(
        segmentoCnaeCollection,
        ...additionalSegmentoCnaes.map(expect.objectContaining),
      );
      expect(comp.segmentoCnaesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Ramo query and add missing value', () => {
      const empresa: IEmpresa = { id: 456 };
      const ramo: IRamo = { id: 31427 };
      empresa.ramo = ramo;

      const ramoCollection: IRamo[] = [{ id: 31280 }];
      jest.spyOn(ramoService, 'query').mockReturnValue(of(new HttpResponse({ body: ramoCollection })));
      const additionalRamos = [ramo];
      const expectedCollection: IRamo[] = [...additionalRamos, ...ramoCollection];
      jest.spyOn(ramoService, 'addRamoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ empresa });
      comp.ngOnInit();

      expect(ramoService.query).toHaveBeenCalled();
      expect(ramoService.addRamoToCollectionIfMissing).toHaveBeenCalledWith(
        ramoCollection,
        ...additionalRamos.map(expect.objectContaining),
      );
      expect(comp.ramosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Tributacao query and add missing value', () => {
      const empresa: IEmpresa = { id: 456 };
      const tributacao: ITributacao = { id: 12805 };
      empresa.tributacao = tributacao;

      const tributacaoCollection: ITributacao[] = [{ id: 18050 }];
      jest.spyOn(tributacaoService, 'query').mockReturnValue(of(new HttpResponse({ body: tributacaoCollection })));
      const additionalTributacaos = [tributacao];
      const expectedCollection: ITributacao[] = [...additionalTributacaos, ...tributacaoCollection];
      jest.spyOn(tributacaoService, 'addTributacaoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ empresa });
      comp.ngOnInit();

      expect(tributacaoService.query).toHaveBeenCalled();
      expect(tributacaoService.addTributacaoToCollectionIfMissing).toHaveBeenCalledWith(
        tributacaoCollection,
        ...additionalTributacaos.map(expect.objectContaining),
      );
      expect(comp.tributacaosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Enquadramento query and add missing value', () => {
      const empresa: IEmpresa = { id: 456 };
      const enquadramento: IEnquadramento = { id: 3507 };
      empresa.enquadramento = enquadramento;

      const enquadramentoCollection: IEnquadramento[] = [{ id: 23832 }];
      jest.spyOn(enquadramentoService, 'query').mockReturnValue(of(new HttpResponse({ body: enquadramentoCollection })));
      const additionalEnquadramentos = [enquadramento];
      const expectedCollection: IEnquadramento[] = [...additionalEnquadramentos, ...enquadramentoCollection];
      jest.spyOn(enquadramentoService, 'addEnquadramentoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ empresa });
      comp.ngOnInit();

      expect(enquadramentoService.query).toHaveBeenCalled();
      expect(enquadramentoService.addEnquadramentoToCollectionIfMissing).toHaveBeenCalledWith(
        enquadramentoCollection,
        ...additionalEnquadramentos.map(expect.objectContaining),
      );
      expect(comp.enquadramentosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const empresa: IEmpresa = { id: 456 };
      const segmentoCnae: ISegmentoCnae = { id: 1233 };
      empresa.segmentoCnaes = [segmentoCnae];
      const ramo: IRamo = { id: 26135 };
      empresa.ramo = ramo;
      const tributacao: ITributacao = { id: 32198 };
      empresa.tributacao = tributacao;
      const enquadramento: IEnquadramento = { id: 24520 };
      empresa.enquadramento = enquadramento;

      activatedRoute.data = of({ empresa });
      comp.ngOnInit();

      expect(comp.segmentoCnaesSharedCollection).toContain(segmentoCnae);
      expect(comp.ramosSharedCollection).toContain(ramo);
      expect(comp.tributacaosSharedCollection).toContain(tributacao);
      expect(comp.enquadramentosSharedCollection).toContain(enquadramento);
      expect(comp.empresa).toEqual(empresa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmpresa>>();
      const empresa = { id: 123 };
      jest.spyOn(empresaFormService, 'getEmpresa').mockReturnValue(empresa);
      jest.spyOn(empresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ empresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: empresa }));
      saveSubject.complete();

      // THEN
      expect(empresaFormService.getEmpresa).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(empresaService.update).toHaveBeenCalledWith(expect.objectContaining(empresa));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmpresa>>();
      const empresa = { id: 123 };
      jest.spyOn(empresaFormService, 'getEmpresa').mockReturnValue({ id: null });
      jest.spyOn(empresaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ empresa: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: empresa }));
      saveSubject.complete();

      // THEN
      expect(empresaFormService.getEmpresa).toHaveBeenCalled();
      expect(empresaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmpresa>>();
      const empresa = { id: 123 };
      jest.spyOn(empresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ empresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(empresaService.update).toHaveBeenCalled();
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

    describe('compareTributacao', () => {
      it('Should forward to tributacaoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(tributacaoService, 'compareTributacao');
        comp.compareTributacao(entity, entity2);
        expect(tributacaoService.compareTributacao).toHaveBeenCalledWith(entity, entity2);
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
  });
});
