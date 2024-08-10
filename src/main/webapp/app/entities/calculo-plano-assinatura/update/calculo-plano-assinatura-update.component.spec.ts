import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IPeriodoPagamento } from 'app/entities/periodo-pagamento/periodo-pagamento.model';
import { PeriodoPagamentoService } from 'app/entities/periodo-pagamento/service/periodo-pagamento.service';
import { IPlanoContaAzul } from 'app/entities/plano-conta-azul/plano-conta-azul.model';
import { PlanoContaAzulService } from 'app/entities/plano-conta-azul/service/plano-conta-azul.service';
import { IPlanoContabil } from 'app/entities/plano-contabil/plano-contabil.model';
import { PlanoContabilService } from 'app/entities/plano-contabil/service/plano-contabil.service';
import { IRamo } from 'app/entities/ramo/ramo.model';
import { RamoService } from 'app/entities/ramo/service/ramo.service';
import { ITributacao } from 'app/entities/tributacao/tributacao.model';
import { TributacaoService } from 'app/entities/tributacao/service/tributacao.service';
import { IDescontoPlanoContabil } from 'app/entities/desconto-plano-contabil/desconto-plano-contabil.model';
import { DescontoPlanoContabilService } from 'app/entities/desconto-plano-contabil/service/desconto-plano-contabil.service';
import { IDescontoPlanoContaAzul } from 'app/entities/desconto-plano-conta-azul/desconto-plano-conta-azul.model';
import { DescontoPlanoContaAzulService } from 'app/entities/desconto-plano-conta-azul/service/desconto-plano-conta-azul.service';
import { IAssinaturaEmpresa } from 'app/entities/assinatura-empresa/assinatura-empresa.model';
import { AssinaturaEmpresaService } from 'app/entities/assinatura-empresa/service/assinatura-empresa.service';
import { ICalculoPlanoAssinatura } from '../calculo-plano-assinatura.model';
import { CalculoPlanoAssinaturaService } from '../service/calculo-plano-assinatura.service';
import { CalculoPlanoAssinaturaFormService } from './calculo-plano-assinatura-form.service';

import { CalculoPlanoAssinaturaUpdateComponent } from './calculo-plano-assinatura-update.component';

describe('CalculoPlanoAssinatura Management Update Component', () => {
  let comp: CalculoPlanoAssinaturaUpdateComponent;
  let fixture: ComponentFixture<CalculoPlanoAssinaturaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let calculoPlanoAssinaturaFormService: CalculoPlanoAssinaturaFormService;
  let calculoPlanoAssinaturaService: CalculoPlanoAssinaturaService;
  let periodoPagamentoService: PeriodoPagamentoService;
  let planoContaAzulService: PlanoContaAzulService;
  let planoContabilService: PlanoContabilService;
  let ramoService: RamoService;
  let tributacaoService: TributacaoService;
  let descontoPlanoContabilService: DescontoPlanoContabilService;
  let descontoPlanoContaAzulService: DescontoPlanoContaAzulService;
  let assinaturaEmpresaService: AssinaturaEmpresaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CalculoPlanoAssinaturaUpdateComponent],
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
      .overrideTemplate(CalculoPlanoAssinaturaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CalculoPlanoAssinaturaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    calculoPlanoAssinaturaFormService = TestBed.inject(CalculoPlanoAssinaturaFormService);
    calculoPlanoAssinaturaService = TestBed.inject(CalculoPlanoAssinaturaService);
    periodoPagamentoService = TestBed.inject(PeriodoPagamentoService);
    planoContaAzulService = TestBed.inject(PlanoContaAzulService);
    planoContabilService = TestBed.inject(PlanoContabilService);
    ramoService = TestBed.inject(RamoService);
    tributacaoService = TestBed.inject(TributacaoService);
    descontoPlanoContabilService = TestBed.inject(DescontoPlanoContabilService);
    descontoPlanoContaAzulService = TestBed.inject(DescontoPlanoContaAzulService);
    assinaturaEmpresaService = TestBed.inject(AssinaturaEmpresaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call PeriodoPagamento query and add missing value', () => {
      const calculoPlanoAssinatura: ICalculoPlanoAssinatura = { id: 456 };
      const periodoPagamento: IPeriodoPagamento = { id: 17338 };
      calculoPlanoAssinatura.periodoPagamento = periodoPagamento;

      const periodoPagamentoCollection: IPeriodoPagamento[] = [{ id: 4093 }];
      jest.spyOn(periodoPagamentoService, 'query').mockReturnValue(of(new HttpResponse({ body: periodoPagamentoCollection })));
      const additionalPeriodoPagamentos = [periodoPagamento];
      const expectedCollection: IPeriodoPagamento[] = [...additionalPeriodoPagamentos, ...periodoPagamentoCollection];
      jest.spyOn(periodoPagamentoService, 'addPeriodoPagamentoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ calculoPlanoAssinatura });
      comp.ngOnInit();

      expect(periodoPagamentoService.query).toHaveBeenCalled();
      expect(periodoPagamentoService.addPeriodoPagamentoToCollectionIfMissing).toHaveBeenCalledWith(
        periodoPagamentoCollection,
        ...additionalPeriodoPagamentos.map(expect.objectContaining),
      );
      expect(comp.periodoPagamentosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call PlanoContaAzul query and add missing value', () => {
      const calculoPlanoAssinatura: ICalculoPlanoAssinatura = { id: 456 };
      const planoContaAzul: IPlanoContaAzul = { id: 13417 };
      calculoPlanoAssinatura.planoContaAzul = planoContaAzul;

      const planoContaAzulCollection: IPlanoContaAzul[] = [{ id: 14125 }];
      jest.spyOn(planoContaAzulService, 'query').mockReturnValue(of(new HttpResponse({ body: planoContaAzulCollection })));
      const additionalPlanoContaAzuls = [planoContaAzul];
      const expectedCollection: IPlanoContaAzul[] = [...additionalPlanoContaAzuls, ...planoContaAzulCollection];
      jest.spyOn(planoContaAzulService, 'addPlanoContaAzulToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ calculoPlanoAssinatura });
      comp.ngOnInit();

      expect(planoContaAzulService.query).toHaveBeenCalled();
      expect(planoContaAzulService.addPlanoContaAzulToCollectionIfMissing).toHaveBeenCalledWith(
        planoContaAzulCollection,
        ...additionalPlanoContaAzuls.map(expect.objectContaining),
      );
      expect(comp.planoContaAzulsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call PlanoContabil query and add missing value', () => {
      const calculoPlanoAssinatura: ICalculoPlanoAssinatura = { id: 456 };
      const planoContabil: IPlanoContabil = { id: 14546 };
      calculoPlanoAssinatura.planoContabil = planoContabil;

      const planoContabilCollection: IPlanoContabil[] = [{ id: 8346 }];
      jest.spyOn(planoContabilService, 'query').mockReturnValue(of(new HttpResponse({ body: planoContabilCollection })));
      const additionalPlanoContabils = [planoContabil];
      const expectedCollection: IPlanoContabil[] = [...additionalPlanoContabils, ...planoContabilCollection];
      jest.spyOn(planoContabilService, 'addPlanoContabilToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ calculoPlanoAssinatura });
      comp.ngOnInit();

      expect(planoContabilService.query).toHaveBeenCalled();
      expect(planoContabilService.addPlanoContabilToCollectionIfMissing).toHaveBeenCalledWith(
        planoContabilCollection,
        ...additionalPlanoContabils.map(expect.objectContaining),
      );
      expect(comp.planoContabilsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Ramo query and add missing value', () => {
      const calculoPlanoAssinatura: ICalculoPlanoAssinatura = { id: 456 };
      const ramo: IRamo = { id: 3343 };
      calculoPlanoAssinatura.ramo = ramo;

      const ramoCollection: IRamo[] = [{ id: 6219 }];
      jest.spyOn(ramoService, 'query').mockReturnValue(of(new HttpResponse({ body: ramoCollection })));
      const additionalRamos = [ramo];
      const expectedCollection: IRamo[] = [...additionalRamos, ...ramoCollection];
      jest.spyOn(ramoService, 'addRamoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ calculoPlanoAssinatura });
      comp.ngOnInit();

      expect(ramoService.query).toHaveBeenCalled();
      expect(ramoService.addRamoToCollectionIfMissing).toHaveBeenCalledWith(
        ramoCollection,
        ...additionalRamos.map(expect.objectContaining),
      );
      expect(comp.ramosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Tributacao query and add missing value', () => {
      const calculoPlanoAssinatura: ICalculoPlanoAssinatura = { id: 456 };
      const tributacao: ITributacao = { id: 31136 };
      calculoPlanoAssinatura.tributacao = tributacao;

      const tributacaoCollection: ITributacao[] = [{ id: 20930 }];
      jest.spyOn(tributacaoService, 'query').mockReturnValue(of(new HttpResponse({ body: tributacaoCollection })));
      const additionalTributacaos = [tributacao];
      const expectedCollection: ITributacao[] = [...additionalTributacaos, ...tributacaoCollection];
      jest.spyOn(tributacaoService, 'addTributacaoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ calculoPlanoAssinatura });
      comp.ngOnInit();

      expect(tributacaoService.query).toHaveBeenCalled();
      expect(tributacaoService.addTributacaoToCollectionIfMissing).toHaveBeenCalledWith(
        tributacaoCollection,
        ...additionalTributacaos.map(expect.objectContaining),
      );
      expect(comp.tributacaosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DescontoPlanoContabil query and add missing value', () => {
      const calculoPlanoAssinatura: ICalculoPlanoAssinatura = { id: 456 };
      const descontoPlanoContabil: IDescontoPlanoContabil = { id: 17203 };
      calculoPlanoAssinatura.descontoPlanoContabil = descontoPlanoContabil;

      const descontoPlanoContabilCollection: IDescontoPlanoContabil[] = [{ id: 27188 }];
      jest.spyOn(descontoPlanoContabilService, 'query').mockReturnValue(of(new HttpResponse({ body: descontoPlanoContabilCollection })));
      const additionalDescontoPlanoContabils = [descontoPlanoContabil];
      const expectedCollection: IDescontoPlanoContabil[] = [...additionalDescontoPlanoContabils, ...descontoPlanoContabilCollection];
      jest.spyOn(descontoPlanoContabilService, 'addDescontoPlanoContabilToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ calculoPlanoAssinatura });
      comp.ngOnInit();

      expect(descontoPlanoContabilService.query).toHaveBeenCalled();
      expect(descontoPlanoContabilService.addDescontoPlanoContabilToCollectionIfMissing).toHaveBeenCalledWith(
        descontoPlanoContabilCollection,
        ...additionalDescontoPlanoContabils.map(expect.objectContaining),
      );
      expect(comp.descontoPlanoContabilsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DescontoPlanoContaAzul query and add missing value', () => {
      const calculoPlanoAssinatura: ICalculoPlanoAssinatura = { id: 456 };
      const descontoPlanoContaAzul: IDescontoPlanoContaAzul = { id: 32160 };
      calculoPlanoAssinatura.descontoPlanoContaAzul = descontoPlanoContaAzul;

      const descontoPlanoContaAzulCollection: IDescontoPlanoContaAzul[] = [{ id: 19253 }];
      jest.spyOn(descontoPlanoContaAzulService, 'query').mockReturnValue(of(new HttpResponse({ body: descontoPlanoContaAzulCollection })));
      const additionalDescontoPlanoContaAzuls = [descontoPlanoContaAzul];
      const expectedCollection: IDescontoPlanoContaAzul[] = [...additionalDescontoPlanoContaAzuls, ...descontoPlanoContaAzulCollection];
      jest.spyOn(descontoPlanoContaAzulService, 'addDescontoPlanoContaAzulToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ calculoPlanoAssinatura });
      comp.ngOnInit();

      expect(descontoPlanoContaAzulService.query).toHaveBeenCalled();
      expect(descontoPlanoContaAzulService.addDescontoPlanoContaAzulToCollectionIfMissing).toHaveBeenCalledWith(
        descontoPlanoContaAzulCollection,
        ...additionalDescontoPlanoContaAzuls.map(expect.objectContaining),
      );
      expect(comp.descontoPlanoContaAzulsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call AssinaturaEmpresa query and add missing value', () => {
      const calculoPlanoAssinatura: ICalculoPlanoAssinatura = { id: 456 };
      const assinaturaEmpresa: IAssinaturaEmpresa = { id: 25848 };
      calculoPlanoAssinatura.assinaturaEmpresa = assinaturaEmpresa;

      const assinaturaEmpresaCollection: IAssinaturaEmpresa[] = [{ id: 28797 }];
      jest.spyOn(assinaturaEmpresaService, 'query').mockReturnValue(of(new HttpResponse({ body: assinaturaEmpresaCollection })));
      const additionalAssinaturaEmpresas = [assinaturaEmpresa];
      const expectedCollection: IAssinaturaEmpresa[] = [...additionalAssinaturaEmpresas, ...assinaturaEmpresaCollection];
      jest.spyOn(assinaturaEmpresaService, 'addAssinaturaEmpresaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ calculoPlanoAssinatura });
      comp.ngOnInit();

      expect(assinaturaEmpresaService.query).toHaveBeenCalled();
      expect(assinaturaEmpresaService.addAssinaturaEmpresaToCollectionIfMissing).toHaveBeenCalledWith(
        assinaturaEmpresaCollection,
        ...additionalAssinaturaEmpresas.map(expect.objectContaining),
      );
      expect(comp.assinaturaEmpresasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const calculoPlanoAssinatura: ICalculoPlanoAssinatura = { id: 456 };
      const periodoPagamento: IPeriodoPagamento = { id: 13100 };
      calculoPlanoAssinatura.periodoPagamento = periodoPagamento;
      const planoContaAzul: IPlanoContaAzul = { id: 29836 };
      calculoPlanoAssinatura.planoContaAzul = planoContaAzul;
      const planoContabil: IPlanoContabil = { id: 18730 };
      calculoPlanoAssinatura.planoContabil = planoContabil;
      const ramo: IRamo = { id: 3134 };
      calculoPlanoAssinatura.ramo = ramo;
      const tributacao: ITributacao = { id: 12364 };
      calculoPlanoAssinatura.tributacao = tributacao;
      const descontoPlanoContabil: IDescontoPlanoContabil = { id: 13647 };
      calculoPlanoAssinatura.descontoPlanoContabil = descontoPlanoContabil;
      const descontoPlanoContaAzul: IDescontoPlanoContaAzul = { id: 28970 };
      calculoPlanoAssinatura.descontoPlanoContaAzul = descontoPlanoContaAzul;
      const assinaturaEmpresa: IAssinaturaEmpresa = { id: 21511 };
      calculoPlanoAssinatura.assinaturaEmpresa = assinaturaEmpresa;

      activatedRoute.data = of({ calculoPlanoAssinatura });
      comp.ngOnInit();

      expect(comp.periodoPagamentosSharedCollection).toContain(periodoPagamento);
      expect(comp.planoContaAzulsSharedCollection).toContain(planoContaAzul);
      expect(comp.planoContabilsSharedCollection).toContain(planoContabil);
      expect(comp.ramosSharedCollection).toContain(ramo);
      expect(comp.tributacaosSharedCollection).toContain(tributacao);
      expect(comp.descontoPlanoContabilsSharedCollection).toContain(descontoPlanoContabil);
      expect(comp.descontoPlanoContaAzulsSharedCollection).toContain(descontoPlanoContaAzul);
      expect(comp.assinaturaEmpresasSharedCollection).toContain(assinaturaEmpresa);
      expect(comp.calculoPlanoAssinatura).toEqual(calculoPlanoAssinatura);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICalculoPlanoAssinatura>>();
      const calculoPlanoAssinatura = { id: 123 };
      jest.spyOn(calculoPlanoAssinaturaFormService, 'getCalculoPlanoAssinatura').mockReturnValue(calculoPlanoAssinatura);
      jest.spyOn(calculoPlanoAssinaturaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ calculoPlanoAssinatura });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: calculoPlanoAssinatura }));
      saveSubject.complete();

      // THEN
      expect(calculoPlanoAssinaturaFormService.getCalculoPlanoAssinatura).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(calculoPlanoAssinaturaService.update).toHaveBeenCalledWith(expect.objectContaining(calculoPlanoAssinatura));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICalculoPlanoAssinatura>>();
      const calculoPlanoAssinatura = { id: 123 };
      jest.spyOn(calculoPlanoAssinaturaFormService, 'getCalculoPlanoAssinatura').mockReturnValue({ id: null });
      jest.spyOn(calculoPlanoAssinaturaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ calculoPlanoAssinatura: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: calculoPlanoAssinatura }));
      saveSubject.complete();

      // THEN
      expect(calculoPlanoAssinaturaFormService.getCalculoPlanoAssinatura).toHaveBeenCalled();
      expect(calculoPlanoAssinaturaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICalculoPlanoAssinatura>>();
      const calculoPlanoAssinatura = { id: 123 };
      jest.spyOn(calculoPlanoAssinaturaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ calculoPlanoAssinatura });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(calculoPlanoAssinaturaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePeriodoPagamento', () => {
      it('Should forward to periodoPagamentoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(periodoPagamentoService, 'comparePeriodoPagamento');
        comp.comparePeriodoPagamento(entity, entity2);
        expect(periodoPagamentoService.comparePeriodoPagamento).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePlanoContaAzul', () => {
      it('Should forward to planoContaAzulService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(planoContaAzulService, 'comparePlanoContaAzul');
        comp.comparePlanoContaAzul(entity, entity2);
        expect(planoContaAzulService.comparePlanoContaAzul).toHaveBeenCalledWith(entity, entity2);
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

    describe('compareDescontoPlanoContabil', () => {
      it('Should forward to descontoPlanoContabilService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(descontoPlanoContabilService, 'compareDescontoPlanoContabil');
        comp.compareDescontoPlanoContabil(entity, entity2);
        expect(descontoPlanoContabilService.compareDescontoPlanoContabil).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareDescontoPlanoContaAzul', () => {
      it('Should forward to descontoPlanoContaAzulService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(descontoPlanoContaAzulService, 'compareDescontoPlanoContaAzul');
        comp.compareDescontoPlanoContaAzul(entity, entity2);
        expect(descontoPlanoContaAzulService.compareDescontoPlanoContaAzul).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareAssinaturaEmpresa', () => {
      it('Should forward to assinaturaEmpresaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(assinaturaEmpresaService, 'compareAssinaturaEmpresa');
        comp.compareAssinaturaEmpresa(entity, entity2);
        expect(assinaturaEmpresaService.compareAssinaturaEmpresa).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
