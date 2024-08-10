import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AdicionalTributacaoDetailComponent } from './adicional-tributacao-detail.component';

describe('AdicionalTributacao Management Detail Component', () => {
  let comp: AdicionalTributacaoDetailComponent;
  let fixture: ComponentFixture<AdicionalTributacaoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdicionalTributacaoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: AdicionalTributacaoDetailComponent,
              resolve: { adicionalTributacao: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AdicionalTributacaoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdicionalTributacaoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load adicionalTributacao on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AdicionalTributacaoDetailComponent);

      // THEN
      expect(instance.adicionalTributacao()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
