import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IDivisaoCnae } from 'app/entities/divisao-cnae/divisao-cnae.model';
import { DivisaoCnaeService } from 'app/entities/divisao-cnae/service/divisao-cnae.service';
import { GrupoCnaeService } from '../service/grupo-cnae.service';
import { IGrupoCnae } from '../grupo-cnae.model';
import { GrupoCnaeFormService } from './grupo-cnae-form.service';

import { GrupoCnaeUpdateComponent } from './grupo-cnae-update.component';

describe('GrupoCnae Management Update Component', () => {
  let comp: GrupoCnaeUpdateComponent;
  let fixture: ComponentFixture<GrupoCnaeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let grupoCnaeFormService: GrupoCnaeFormService;
  let grupoCnaeService: GrupoCnaeService;
  let divisaoCnaeService: DivisaoCnaeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [GrupoCnaeUpdateComponent],
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
      .overrideTemplate(GrupoCnaeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(GrupoCnaeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    grupoCnaeFormService = TestBed.inject(GrupoCnaeFormService);
    grupoCnaeService = TestBed.inject(GrupoCnaeService);
    divisaoCnaeService = TestBed.inject(DivisaoCnaeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call DivisaoCnae query and add missing value', () => {
      const grupoCnae: IGrupoCnae = { id: 456 };
      const divisao: IDivisaoCnae = { id: 30122 };
      grupoCnae.divisao = divisao;

      const divisaoCnaeCollection: IDivisaoCnae[] = [{ id: 28800 }];
      jest.spyOn(divisaoCnaeService, 'query').mockReturnValue(of(new HttpResponse({ body: divisaoCnaeCollection })));
      const additionalDivisaoCnaes = [divisao];
      const expectedCollection: IDivisaoCnae[] = [...additionalDivisaoCnaes, ...divisaoCnaeCollection];
      jest.spyOn(divisaoCnaeService, 'addDivisaoCnaeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ grupoCnae });
      comp.ngOnInit();

      expect(divisaoCnaeService.query).toHaveBeenCalled();
      expect(divisaoCnaeService.addDivisaoCnaeToCollectionIfMissing).toHaveBeenCalledWith(
        divisaoCnaeCollection,
        ...additionalDivisaoCnaes.map(expect.objectContaining),
      );
      expect(comp.divisaoCnaesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const grupoCnae: IGrupoCnae = { id: 456 };
      const divisao: IDivisaoCnae = { id: 30428 };
      grupoCnae.divisao = divisao;

      activatedRoute.data = of({ grupoCnae });
      comp.ngOnInit();

      expect(comp.divisaoCnaesSharedCollection).toContain(divisao);
      expect(comp.grupoCnae).toEqual(grupoCnae);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGrupoCnae>>();
      const grupoCnae = { id: 123 };
      jest.spyOn(grupoCnaeFormService, 'getGrupoCnae').mockReturnValue(grupoCnae);
      jest.spyOn(grupoCnaeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ grupoCnae });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: grupoCnae }));
      saveSubject.complete();

      // THEN
      expect(grupoCnaeFormService.getGrupoCnae).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(grupoCnaeService.update).toHaveBeenCalledWith(expect.objectContaining(grupoCnae));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGrupoCnae>>();
      const grupoCnae = { id: 123 };
      jest.spyOn(grupoCnaeFormService, 'getGrupoCnae').mockReturnValue({ id: null });
      jest.spyOn(grupoCnaeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ grupoCnae: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: grupoCnae }));
      saveSubject.complete();

      // THEN
      expect(grupoCnaeFormService.getGrupoCnae).toHaveBeenCalled();
      expect(grupoCnaeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGrupoCnae>>();
      const grupoCnae = { id: 123 };
      jest.spyOn(grupoCnaeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ grupoCnae });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(grupoCnaeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareDivisaoCnae', () => {
      it('Should forward to divisaoCnaeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(divisaoCnaeService, 'compareDivisaoCnae');
        comp.compareDivisaoCnae(entity, entity2);
        expect(divisaoCnaeService.compareDivisaoCnae).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
