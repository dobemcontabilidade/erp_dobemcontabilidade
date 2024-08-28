import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { DataUtils } from 'app/core/util/data-util.service';

import { EmpresaDetailComponent } from './empresa-detail.component';

describe('Empresa Management Detail Component', () => {
  let comp: EmpresaDetailComponent;
  let fixture: ComponentFixture<EmpresaDetailComponent>;
  let dataUtils: DataUtils;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EmpresaDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: EmpresaDetailComponent,
              resolve: { empresa: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(EmpresaDetailComponent, '')
      .compileComponents();
    dataUtils = TestBed.inject(DataUtils);
    jest.spyOn(window, 'open').mockImplementation(() => null);
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EmpresaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load empresa on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', EmpresaDetailComponent);

      // THEN
      expect(instance.empresa()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });

  describe('byteSize', () => {
    it('Should call byteSize from DataUtils', () => {
      // GIVEN
      jest.spyOn(dataUtils, 'byteSize');
      const fakeBase64 = 'fake base64';

      // WHEN
      comp.byteSize(fakeBase64);

      // THEN
      expect(dataUtils.byteSize).toHaveBeenCalledWith(fakeBase64);
    });
  });

  describe('openFile', () => {
    it('Should call openFile from DataUtils', () => {
      const newWindow = { ...window };
      newWindow.document.write = jest.fn();
      window.open = jest.fn(() => newWindow);
      window.onload = jest.fn(() => newWindow) as any;
      window.URL.createObjectURL = jest.fn() as any;
      // GIVEN
      jest.spyOn(dataUtils, 'openFile');
      const fakeContentType = 'fake content type';
      const fakeBase64 = 'fake base64';

      // WHEN
      comp.openFile(fakeBase64, fakeContentType);

      // THEN
      expect(dataUtils.openFile).toHaveBeenCalledWith(fakeBase64, fakeContentType);
    });
  });
});
