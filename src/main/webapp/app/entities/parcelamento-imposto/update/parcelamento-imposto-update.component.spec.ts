import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IImposto } from 'app/entities/imposto/imposto.model';
import { ImpostoService } from 'app/entities/imposto/service/imposto.service';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';
import { IParcelamentoImposto } from '../parcelamento-imposto.model';
import { ParcelamentoImpostoService } from '../service/parcelamento-imposto.service';
import { ParcelamentoImpostoFormService } from './parcelamento-imposto-form.service';

import { ParcelamentoImpostoUpdateComponent } from './parcelamento-imposto-update.component';

describe('ParcelamentoImposto Management Update Component', () => {
  let comp: ParcelamentoImpostoUpdateComponent;
  let fixture: ComponentFixture<ParcelamentoImpostoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let parcelamentoImpostoFormService: ParcelamentoImpostoFormService;
  let parcelamentoImpostoService: ParcelamentoImpostoService;
  let impostoService: ImpostoService;
  let empresaService: EmpresaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ParcelamentoImpostoUpdateComponent],
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
      .overrideTemplate(ParcelamentoImpostoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ParcelamentoImpostoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    parcelamentoImpostoFormService = TestBed.inject(ParcelamentoImpostoFormService);
    parcelamentoImpostoService = TestBed.inject(ParcelamentoImpostoService);
    impostoService = TestBed.inject(ImpostoService);
    empresaService = TestBed.inject(EmpresaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Imposto query and add missing value', () => {
      const parcelamentoImposto: IParcelamentoImposto = { id: 456 };
      const imposto: IImposto = { id: 27343 };
      parcelamentoImposto.imposto = imposto;

      const impostoCollection: IImposto[] = [{ id: 32066 }];
      jest.spyOn(impostoService, 'query').mockReturnValue(of(new HttpResponse({ body: impostoCollection })));
      const additionalImpostos = [imposto];
      const expectedCollection: IImposto[] = [...additionalImpostos, ...impostoCollection];
      jest.spyOn(impostoService, 'addImpostoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ parcelamentoImposto });
      comp.ngOnInit();

      expect(impostoService.query).toHaveBeenCalled();
      expect(impostoService.addImpostoToCollectionIfMissing).toHaveBeenCalledWith(
        impostoCollection,
        ...additionalImpostos.map(expect.objectContaining),
      );
      expect(comp.impostosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Empresa query and add missing value', () => {
      const parcelamentoImposto: IParcelamentoImposto = { id: 456 };
      const empresa: IEmpresa = { id: 23967 };
      parcelamentoImposto.empresa = empresa;

      const empresaCollection: IEmpresa[] = [{ id: 3251 }];
      jest.spyOn(empresaService, 'query').mockReturnValue(of(new HttpResponse({ body: empresaCollection })));
      const additionalEmpresas = [empresa];
      const expectedCollection: IEmpresa[] = [...additionalEmpresas, ...empresaCollection];
      jest.spyOn(empresaService, 'addEmpresaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ parcelamentoImposto });
      comp.ngOnInit();

      expect(empresaService.query).toHaveBeenCalled();
      expect(empresaService.addEmpresaToCollectionIfMissing).toHaveBeenCalledWith(
        empresaCollection,
        ...additionalEmpresas.map(expect.objectContaining),
      );
      expect(comp.empresasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const parcelamentoImposto: IParcelamentoImposto = { id: 456 };
      const imposto: IImposto = { id: 21647 };
      parcelamentoImposto.imposto = imposto;
      const empresa: IEmpresa = { id: 5106 };
      parcelamentoImposto.empresa = empresa;

      activatedRoute.data = of({ parcelamentoImposto });
      comp.ngOnInit();

      expect(comp.impostosSharedCollection).toContain(imposto);
      expect(comp.empresasSharedCollection).toContain(empresa);
      expect(comp.parcelamentoImposto).toEqual(parcelamentoImposto);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IParcelamentoImposto>>();
      const parcelamentoImposto = { id: 123 };
      jest.spyOn(parcelamentoImpostoFormService, 'getParcelamentoImposto').mockReturnValue(parcelamentoImposto);
      jest.spyOn(parcelamentoImpostoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ parcelamentoImposto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: parcelamentoImposto }));
      saveSubject.complete();

      // THEN
      expect(parcelamentoImpostoFormService.getParcelamentoImposto).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(parcelamentoImpostoService.update).toHaveBeenCalledWith(expect.objectContaining(parcelamentoImposto));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IParcelamentoImposto>>();
      const parcelamentoImposto = { id: 123 };
      jest.spyOn(parcelamentoImpostoFormService, 'getParcelamentoImposto').mockReturnValue({ id: null });
      jest.spyOn(parcelamentoImpostoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ parcelamentoImposto: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: parcelamentoImposto }));
      saveSubject.complete();

      // THEN
      expect(parcelamentoImpostoFormService.getParcelamentoImposto).toHaveBeenCalled();
      expect(parcelamentoImpostoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IParcelamentoImposto>>();
      const parcelamentoImposto = { id: 123 };
      jest.spyOn(parcelamentoImpostoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ parcelamentoImposto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(parcelamentoImpostoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareImposto', () => {
      it('Should forward to impostoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(impostoService, 'compareImposto');
        comp.compareImposto(entity, entity2);
        expect(impostoService.compareImposto).toHaveBeenCalledWith(entity, entity2);
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
  });
});
