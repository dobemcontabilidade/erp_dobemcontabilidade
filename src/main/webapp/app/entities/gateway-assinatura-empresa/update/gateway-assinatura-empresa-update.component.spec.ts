import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IAssinaturaEmpresa } from 'app/entities/assinatura-empresa/assinatura-empresa.model';
import { AssinaturaEmpresaService } from 'app/entities/assinatura-empresa/service/assinatura-empresa.service';
import { IGatewayPagamento } from 'app/entities/gateway-pagamento/gateway-pagamento.model';
import { GatewayPagamentoService } from 'app/entities/gateway-pagamento/service/gateway-pagamento.service';
import { IGatewayAssinaturaEmpresa } from '../gateway-assinatura-empresa.model';
import { GatewayAssinaturaEmpresaService } from '../service/gateway-assinatura-empresa.service';
import { GatewayAssinaturaEmpresaFormService } from './gateway-assinatura-empresa-form.service';

import { GatewayAssinaturaEmpresaUpdateComponent } from './gateway-assinatura-empresa-update.component';

describe('GatewayAssinaturaEmpresa Management Update Component', () => {
  let comp: GatewayAssinaturaEmpresaUpdateComponent;
  let fixture: ComponentFixture<GatewayAssinaturaEmpresaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let gatewayAssinaturaEmpresaFormService: GatewayAssinaturaEmpresaFormService;
  let gatewayAssinaturaEmpresaService: GatewayAssinaturaEmpresaService;
  let assinaturaEmpresaService: AssinaturaEmpresaService;
  let gatewayPagamentoService: GatewayPagamentoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [GatewayAssinaturaEmpresaUpdateComponent],
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
      .overrideTemplate(GatewayAssinaturaEmpresaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(GatewayAssinaturaEmpresaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    gatewayAssinaturaEmpresaFormService = TestBed.inject(GatewayAssinaturaEmpresaFormService);
    gatewayAssinaturaEmpresaService = TestBed.inject(GatewayAssinaturaEmpresaService);
    assinaturaEmpresaService = TestBed.inject(AssinaturaEmpresaService);
    gatewayPagamentoService = TestBed.inject(GatewayPagamentoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call AssinaturaEmpresa query and add missing value', () => {
      const gatewayAssinaturaEmpresa: IGatewayAssinaturaEmpresa = { id: 456 };
      const assinaturaEmpresa: IAssinaturaEmpresa = { id: 4038 };
      gatewayAssinaturaEmpresa.assinaturaEmpresa = assinaturaEmpresa;

      const assinaturaEmpresaCollection: IAssinaturaEmpresa[] = [{ id: 8415 }];
      jest.spyOn(assinaturaEmpresaService, 'query').mockReturnValue(of(new HttpResponse({ body: assinaturaEmpresaCollection })));
      const additionalAssinaturaEmpresas = [assinaturaEmpresa];
      const expectedCollection: IAssinaturaEmpresa[] = [...additionalAssinaturaEmpresas, ...assinaturaEmpresaCollection];
      jest.spyOn(assinaturaEmpresaService, 'addAssinaturaEmpresaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ gatewayAssinaturaEmpresa });
      comp.ngOnInit();

      expect(assinaturaEmpresaService.query).toHaveBeenCalled();
      expect(assinaturaEmpresaService.addAssinaturaEmpresaToCollectionIfMissing).toHaveBeenCalledWith(
        assinaturaEmpresaCollection,
        ...additionalAssinaturaEmpresas.map(expect.objectContaining),
      );
      expect(comp.assinaturaEmpresasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call GatewayPagamento query and add missing value', () => {
      const gatewayAssinaturaEmpresa: IGatewayAssinaturaEmpresa = { id: 456 };
      const gatewayPagamento: IGatewayPagamento = { id: 21400 };
      gatewayAssinaturaEmpresa.gatewayPagamento = gatewayPagamento;

      const gatewayPagamentoCollection: IGatewayPagamento[] = [{ id: 12920 }];
      jest.spyOn(gatewayPagamentoService, 'query').mockReturnValue(of(new HttpResponse({ body: gatewayPagamentoCollection })));
      const additionalGatewayPagamentos = [gatewayPagamento];
      const expectedCollection: IGatewayPagamento[] = [...additionalGatewayPagamentos, ...gatewayPagamentoCollection];
      jest.spyOn(gatewayPagamentoService, 'addGatewayPagamentoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ gatewayAssinaturaEmpresa });
      comp.ngOnInit();

      expect(gatewayPagamentoService.query).toHaveBeenCalled();
      expect(gatewayPagamentoService.addGatewayPagamentoToCollectionIfMissing).toHaveBeenCalledWith(
        gatewayPagamentoCollection,
        ...additionalGatewayPagamentos.map(expect.objectContaining),
      );
      expect(comp.gatewayPagamentosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const gatewayAssinaturaEmpresa: IGatewayAssinaturaEmpresa = { id: 456 };
      const assinaturaEmpresa: IAssinaturaEmpresa = { id: 27735 };
      gatewayAssinaturaEmpresa.assinaturaEmpresa = assinaturaEmpresa;
      const gatewayPagamento: IGatewayPagamento = { id: 8135 };
      gatewayAssinaturaEmpresa.gatewayPagamento = gatewayPagamento;

      activatedRoute.data = of({ gatewayAssinaturaEmpresa });
      comp.ngOnInit();

      expect(comp.assinaturaEmpresasSharedCollection).toContain(assinaturaEmpresa);
      expect(comp.gatewayPagamentosSharedCollection).toContain(gatewayPagamento);
      expect(comp.gatewayAssinaturaEmpresa).toEqual(gatewayAssinaturaEmpresa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGatewayAssinaturaEmpresa>>();
      const gatewayAssinaturaEmpresa = { id: 123 };
      jest.spyOn(gatewayAssinaturaEmpresaFormService, 'getGatewayAssinaturaEmpresa').mockReturnValue(gatewayAssinaturaEmpresa);
      jest.spyOn(gatewayAssinaturaEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ gatewayAssinaturaEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: gatewayAssinaturaEmpresa }));
      saveSubject.complete();

      // THEN
      expect(gatewayAssinaturaEmpresaFormService.getGatewayAssinaturaEmpresa).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(gatewayAssinaturaEmpresaService.update).toHaveBeenCalledWith(expect.objectContaining(gatewayAssinaturaEmpresa));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGatewayAssinaturaEmpresa>>();
      const gatewayAssinaturaEmpresa = { id: 123 };
      jest.spyOn(gatewayAssinaturaEmpresaFormService, 'getGatewayAssinaturaEmpresa').mockReturnValue({ id: null });
      jest.spyOn(gatewayAssinaturaEmpresaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ gatewayAssinaturaEmpresa: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: gatewayAssinaturaEmpresa }));
      saveSubject.complete();

      // THEN
      expect(gatewayAssinaturaEmpresaFormService.getGatewayAssinaturaEmpresa).toHaveBeenCalled();
      expect(gatewayAssinaturaEmpresaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGatewayAssinaturaEmpresa>>();
      const gatewayAssinaturaEmpresa = { id: 123 };
      jest.spyOn(gatewayAssinaturaEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ gatewayAssinaturaEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(gatewayAssinaturaEmpresaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareAssinaturaEmpresa', () => {
      it('Should forward to assinaturaEmpresaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(assinaturaEmpresaService, 'compareAssinaturaEmpresa');
        comp.compareAssinaturaEmpresa(entity, entity2);
        expect(assinaturaEmpresaService.compareAssinaturaEmpresa).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareGatewayPagamento', () => {
      it('Should forward to gatewayPagamentoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(gatewayPagamentoService, 'compareGatewayPagamento');
        comp.compareGatewayPagamento(entity, entity2);
        expect(gatewayPagamentoService.compareGatewayPagamento).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
